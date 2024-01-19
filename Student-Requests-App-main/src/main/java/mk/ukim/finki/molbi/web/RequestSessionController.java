package mk.ukim.finki.molbi.web;

import lombok.AllArgsConstructor;
import mk.ukim.finki.molbi.model.dtos.RequestSessionDto;
import mk.ukim.finki.molbi.model.enums.RequestType;
import mk.ukim.finki.molbi.model.requests.RequestSession;
import mk.ukim.finki.molbi.service.RequestSessionService;
import mk.ukim.finki.molbi.service.SemesterService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/")
@AllArgsConstructor
public class RequestSessionController {

    private final RequestSessionService requestSessionService;
    private final SemesterService semesterService;

    @GetMapping
    public String listRequestSessions(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer results,
            Model model
    ) {
        Page<RequestSession> sessionPage = requestSessionService.getAllEntities(pageNum, results);
        model.addAttribute("sessionPage", sessionPage);
        return "request_session/list-request-session";
    }

    @GetMapping("/{id}/edit")
    public String editProductPage(@PathVariable Long id, Model model) {
        RequestSession session = this.requestSessionService.findById(id);
        model.addAttribute("semesters", semesterService.findAll());
        model.addAttribute("requestTypes", RequestType.values());
        model.addAttribute("requestSession", session);
        return "request_session/add-request-session";
    }

    @GetMapping("/add")
    public String addProductPage(Model model) {
        model.addAttribute("semesters", semesterService.findAll());
        model.addAttribute("requestTypes", RequestType.values());
        return "request_session/add-request-session";
    }

    @PostMapping("/save")
    public String saveRequestSession(
            @RequestParam(required = false) Long id,
            @ModelAttribute RequestSessionDto dto
    ) {
        requestSessionService.save(id, dto);
        return "redirect:/";
    }


    @PostMapping("/{id}/delete")
    public String deleteRequestSession(@PathVariable Long id) {
        requestSessionService.isSafeToDelete(id);
        requestSessionService.delete(id);
        return "redirect:/";
    }
}
