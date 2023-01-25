package com.example.stareshop.controller;

import com.example.stareshop.model.Inventory;
import com.example.stareshop.model.Request;
import com.example.stareshop.model.User;
import com.example.stareshop.services.BusinessService;
import com.example.stareshop.services.PendingService;
import com.example.stareshop.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/pending")
@RequiredArgsConstructor
public class PendingController {
    private final PendingService pendingService;
    private final UserService userService;
    private final BusinessService businessService;
    private final JavaMailSender javaMailSender;

    @GetMapping("/raw")
    public ResponseEntity getAllPending() {
        return ResponseEntity.ok(pendingService.getAll());
    }

    @GetMapping
    public ModelAndView getPendingsPage() {
        ModelAndView modelAndView = new ModelAndView();
        var pendings = pendingService.getAll();
        modelAndView.addObject("pendings", pendings);
        modelAndView.setViewName("acceptPendings");
        return modelAndView;
    }

    @PostMapping("/decline/{pendingId}")
    public ModelAndView declineConfirm(@PathVariable Long pendingId) {
        ModelAndView modelAndView = new ModelAndView();

        var pending = pendingService.getById(pendingId);

        if (pending.isPresent()) {
            pendingService.delete(pendingId);
            businessService.delete(pending.get().getBusiness().getId());
            return new ModelAndView("redirect:/pending");
        } else {
            modelAndView.setViewName("errorWithMessage");
            modelAndView.addObject("errorMessage", "No pending with this id found!");
            return modelAndView;
        }
    }

    @PostMapping("/accept/{pendingId}")
    public ModelAndView acceptConfirm(@PathVariable Long pendingId) {
        ModelAndView modelAndView = new ModelAndView();

        var pending = pendingService.getById(pendingId);

        if (pending.isPresent()) {
            pendingService.delete(pendingId);
            pending.get().getBusiness().setIsAccepted(true);
            businessService.addOrUpdate(pending.get().getBusiness());

            Optional<User> currentUser = userService.getByEmail( pending.get().getUser().getEmail());

            if (currentUser.isPresent()) {
                currentUser.get().setBusinesses(pending.get().getBusiness());

                if (Objects.equals(pending.get().getBusiness().getType(), "B2C")) {
                    userService.updateRole(currentUser.get().getId(), "BToCAdmin", pending.get().getBusiness().getId());
                } else if (Objects.equals(pending.get().getBusiness().getType(), "B2B")) {
                    userService.updateRole(currentUser.get().getId(), "BToBAdmin", pending.get().getBusiness().getId());
                }

                try {
                    sendMail(currentUser.get().getEmail(), currentUser.get().getBusinesses().getName());
                } catch (MessagingException | UnsupportedEncodingException e) {
                    throw new RuntimeException(e);
                }

            } else {
                modelAndView.setViewName("errorWithMessage");
                modelAndView.addObject("errorMessage", "No user with this id found!");
                return modelAndView;
            }

            return new ModelAndView("redirect:/pending");
        } else {
            modelAndView.setViewName("errorWithMessage");
            modelAndView.addObject("errorMessage", "No pending with this id found!");
            return modelAndView;
        }
    }

    private void sendMail(String email, String businessName) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("stare_shop@gmail.com", "Stare Shop");
        helper.setTo(email);
        helper.setSubject("Business accepted");

        String content = "<p>Congratulations \uD83D\uDCBC \uD83D\uDCC8 \uD83E\uDD1D \uD83D\uDCCA!</p>"
                + "<p>Your business " + businessName + " got accepted!</p>"
                + "<p>Welcome to Stare Shop!</p>"
                + "<a href=\"http://localhost:8080/\">"
                + "<input type=\"submit\" value=\"Shop Now!\"/>"
                + "</a>";

        helper.setText(content, true);

        javaMailSender.send(message);
    }
}
