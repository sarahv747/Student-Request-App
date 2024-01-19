package mk.ukim.finki.molbi.web;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mk.ukim.finki.molbi.model.dtos.FilterDto;
import mk.ukim.finki.molbi.model.dtos.LateCourseEnrollmentStudentRequestDto;
import mk.ukim.finki.molbi.model.exceptions.AllFieldsNotFilledException;
import mk.ukim.finki.molbi.model.exceptions.RequestAlreadyProcessedOrAlreadyRejectedException;
import mk.ukim.finki.molbi.model.requests.LateCourseEnrollmentStudentRequest;
import mk.ukim.finki.molbi.model.requests.RequestSession;
import mk.ukim.finki.molbi.service.CourseService;
import mk.ukim.finki.molbi.service.LateCourseEnrollmentStudentRequestService;
import mk.ukim.finki.molbi.service.ProfessorService;
import mk.ukim.finki.molbi.service.RequestSessionService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping(value = "/LATE_COURSE_ENROLLMENT")
@AllArgsConstructor
public class LateCourseEnrollmentStudentRequestController {

    private final LateCourseEnrollmentStudentRequestService requestService;
    private final RequestSessionService requestSessionService;
    private final CourseService courseService;
    private final ProfessorService professorService;

    @GetMapping("/{sessionId}")
    public String showRequests(@PathVariable Long sessionId,
                               @RequestParam(defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "10") Integer results,
                               @ModelAttribute FilterDto dto,
                               Model model) {

        Page<LateCourseEnrollmentStudentRequest> page =
                requestService.findByRequestSessionWithPagination(sessionId, pageNum, results);
        if (dto.getProfessorApproved() != null || dto.getApproved() != null || dto.getProcessed() != null ||
                dto.getStudent() != null || dto.getProfessor() != null) {
            page = requestService.findByRequestSessionFilteredWithPagination(sessionId, pageNum, results, dto);
        }

        model.addAttribute("page", page);
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("professors", professorService.listAll());
        model.addAttribute("filterDto", dto);

        return "lce/list-lce";
    }


    @GetMapping({"/{sessionId}/apply", "/{sessionId}/edit/{id}"})
    public String showFormPage(@PathVariable(required = false) Long sessionId,
                               @PathVariable(required = false) Long id,
                               Model model,
                               @RequestParam(required = false) String error) {
        if (error != null) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }

        if (id != null) {
            LateCourseEnrollmentStudentRequest request = requestService.findById(id);
            model.addAttribute("request", request);
        }
        RequestSession requestSession = requestSessionService.findById(sessionId);
        model.addAttribute("courses", courseService.listBySemester(requestSession.getSemester()));
        model.addAttribute("professors", professorService.listAll());
        model.addAttribute("sessionId", sessionId);

        return "lce/form-lce";
    }

    @PostMapping("/apply")
    public String applyForRequest(@ModelAttribute LateCourseEnrollmentStudentRequestDto dto,
                                  @RequestParam(required = false) Long id) {
        try {
            if (id != null) {
                requestService.edit(id, dto);
            } else {
                requestService.create(dto);
            }
            return "redirect:/LATE_COURSE_ENROLLMENT/" + dto.getRequestSession();
        } catch (RuntimeException e) {
            if (id != null) {
                return "redirect:/LATE_COURSE_ENROLLMENT/" + dto.getRequestSession() + "/edit/" + id + "?error=" + e.getMessage();
            } else {
                return "redirect:/LATE_COURSE_ENROLLMENT/" + dto.getRequestSession() + "/apply?error=" + e.getMessage();
            }
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteRequest(@PathVariable Long id) {
        LateCourseEnrollmentStudentRequest request = requestService.findById(id);
        requestService.delete(id);
        return "redirect:/LATE_COURSE_ENROLLMENT/" + request.getRequestSession().getId();
    }

    @PostMapping("/processApprovalByProfessor/{id}")
    public String processApprovalByProfessorOfRequest(@PathVariable Long id) {
        LateCourseEnrollmentStudentRequest request = requestService.findById(id);
        requestService.processApprovalByProfessor(id);
        return "redirect:/LATE_COURSE_ENROLLMENT/" + request.getRequestSession().getId();
    }

    @GetMapping("/details/{id}")
    public String showDetailsPage(@PathVariable Long id,
                                  Model model,
                                  @RequestParam(required = false) boolean reject,
                                  @RequestParam(required = false) String error,
                                  HttpSession session) {
        LateCourseEnrollmentStudentRequest request = requestService.findById(id);
        if (request.getIsApproved() != null && !request.getIsApproved() && reject) {
            throw new RequestAlreadyProcessedOrAlreadyRejectedException(request);
        }
        if (error != null) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        model.addAttribute("reject", reject);
        model.addAttribute("rejectionReason", session.getAttribute("rejectionReason"));


        model.addAttribute("request", request);
        return "lce/details-lce";
    }

    @PostMapping("/updateStatus/{id}")
    public String updateRequestStatus(@PathVariable Long id,
                                      @RequestParam Boolean action,
                                      @RequestParam(required = false) String rejectionReason,
                                      HttpSession session) {
        if (!action && rejectionReason.isEmpty()) {
            return "redirect:/LATE_COURSE_ENROLLMENT/details/{id}?error=" + new AllFieldsNotFilledException().getMessage();
        }
        LateCourseEnrollmentStudentRequest request = requestService.findById(id);
        requestService.updateStatus(id, action, rejectionReason);
        session.setAttribute("rejectionReason", rejectionReason);
        return "redirect:/LATE_COURSE_ENROLLMENT/" + request.getRequestSession().getId();
    }

    @PostMapping("/process/{id}")
    public String markAsProcessedRequest(@PathVariable Long id) {
        LateCourseEnrollmentStudentRequest request = requestService.findById(id);
        requestService.markAsProcessed(id);
        return "redirect:/LATE_COURSE_ENROLLMENT/" + request.getRequestSession().getId();
    }

    @ExceptionHandler(RuntimeException.class)
    public String handleExceptions(RuntimeException ex, Model model) {
        model.addAttribute("exceptionMessage", ex.getMessage());
        return "error";
    }

}
