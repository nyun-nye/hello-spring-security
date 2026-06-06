package kr.ac.hansung.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import kr.ac.hansung.dto.PasswordChangeDto;
import kr.ac.hansung.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/user/password")
    public String passwordForm(Model model) {
        model.addAttribute("dto", new PasswordChangeDto());
        return "user/password";
    }

    // controller/UserController.java
    @PostMapping("/user/password")
    public String changePassword(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @ModelAttribute("dto") PasswordChangeDto dto,
            BindingResult bindingResult,
            Model model,
            RedirectAttributes ra) {
        if (bindingResult.hasErrors()) {
            preserveForm(model, dto, bindingResult);
            return "user/password";
        }
        if (!dto.getNewPassword().equals(dto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "mismatch",
                "새 비밀번호가 일치하지 않습니다");
            preserveForm(model, dto, bindingResult);
            return "user/password";
        }
        try {
            userService.changePassword(userDetails.getUsername(),
                dto.getCurrentPassword(), dto.getNewPassword());
            ra.addFlashAttribute("successMessage", "비밀번호가 변경되었습니다");
        } catch (IllegalArgumentException e) {
            bindingResult.rejectValue("currentPassword", "wrong", e.getMessage());
            preserveForm(model, dto, bindingResult);
            return "user/password";
        }
        return "redirect:/home";
    }

    // Thymeleaf #fields가 오류 메시지를 읽으려면 dto와 BindingResult를 함께 model에 유지해야 함
    private void preserveForm(Model model, PasswordChangeDto dto, BindingResult bindingResult) {
        model.addAttribute("dto", dto);
        model.addAttribute(BindingResult.MODEL_KEY_PREFIX + "dto", bindingResult);
    }
}
