package kr.ac.hansung.config;

import kr.ac.hansung.support.AssignmentFooterSupport;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class AssignmentModelAdvice {

    private final AssignmentFooterSupport assignmentFooterSupport;

    public AssignmentModelAdvice(AssignmentFooterSupport assignmentFooterSupport) {
        this.assignmentFooterSupport = assignmentFooterSupport;
    }

    @ModelAttribute
    public void addAssignmentFooter(Model model) {
        model.addAttribute("assignmentFooterLine", assignmentFooterSupport.footerLine());
    }
}
