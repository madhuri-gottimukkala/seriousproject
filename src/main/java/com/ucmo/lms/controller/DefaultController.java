package com.ucmo.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.ucmo.lms.configuration.SecurityConfig;
import com.ucmo.lms.service.ArtifactService;
import com.ucmo.lms.service.LoginService;
import com.ucmo.lms.service.MemberService;

@Controller
public class DefaultController {
  @Autowired
  LoginService loginService;

  @Autowired
  MemberService memberService;

  @Autowired
  ArtifactService artifactService;

  @Autowired
  SecurityConfig securityConfig;

  @GetMapping("/")
  public String indexView(Model model, Authentication authentication) {
    loginService.addMemberToModel(model, authentication);
    model.addAttribute("latestArtifacts", artifactService.getLatestArtifacts());
    model.addAttribute("popularArtifacts", artifactService.getPopularArtifacts());
    return "index.html";
  }
}