package mk.ukim.finki.molbi.web;

import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import mk.ukim.finki.molbi.model.dtos.GeneralStudentRequestDto;
import mk.ukim.finki.molbi.model.requests.GeneralStudentRequest;
import mk.ukim.finki.molbi.service.GeneralStudentRequestService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/GENERAL")
@AllArgsConstructor
public class GeneralStudentRequestController {
    private final GeneralStudentRequestService generalStudentRequestService;

    @GetMapping("{requestSessionId}")
    public String showAllGeneralStudentRequests(@PathVariable Long requestSessionId, Model model,
                                                @RequestParam(defaultValue = "1") Integer pageNum,
                                                @RequestParam(defaultValue = "10") Integer results,
                                                @RequestParam(name= "student", required = false) String studentFilter,
                                                @RequestParam(name = "isApproved", required = false) Boolean isApprovedFilter,
                                                @RequestParam(name = "isProcessed", required = false) Boolean isProcessedFilter,
                                                HttpSession session) {

        Page<GeneralStudentRequest> page =
            generalStudentRequestService.showPage(requestSessionId, pageNum, results,
                    studentFilter, isApprovedFilter, isProcessedFilter);

        String denyReason = (String) session.getAttribute("denyReason");
        model.addAttribute("denyReason", denyReason);

        model.addAttribute("generalRequests", page);
        model.addAttribute("requestSessionId", requestSessionId);
        return "general/generalRequests";
    }

    @GetMapping("{requestSessionId}/apply")
    public String showAddPage(@PathVariable Long requestSessionId, Model model) {
        model.addAttribute("requestSessionId", requestSessionId);
//        model.addAttribute("students", studentService.listAll());
        return "general/generalRequestsAdd";
    }

    @PostMapping("{requestSessionId}/apply")
    public String addRequest(@PathVariable Long requestSessionId, @ModelAttribute GeneralStudentRequestDto dto) {
        generalStudentRequestService.create(dto);
        return "redirect:/GENERAL/" + requestSessionId;
    }

    @PostMapping("{requestSessionId}/edit/{id}")
    public String editRequest(@PathVariable Long requestSessionId,
                              @PathVariable Long id,
                              @RequestParam String action,
                              @RequestParam(required = false, defaultValue = "") String reason,
                              HttpSession session) {
        if ("approve".equals(action)) {
            generalStudentRequestService.status(id, Boolean.TRUE, reason);
        } else if ("deny".equals(action)) {
            generalStudentRequestService.status(id, Boolean.FALSE, reason);
            session.setAttribute("denyReason", reason);
        } else if ("markProcessed".equals(action)) {
            generalStudentRequestService.markProcessed(id, Boolean.TRUE);
        }
        return "redirect:/GENERAL/" + requestSessionId;
    }

    @PostMapping("{requestSessionId}/delete/{id}")
    public String deleteRequest(@PathVariable Long requestSessionId, @PathVariable Long id) {
        generalStudentRequestService.delete(id);
        return "redirect:/GENERAL/" + requestSessionId;
    }
}
