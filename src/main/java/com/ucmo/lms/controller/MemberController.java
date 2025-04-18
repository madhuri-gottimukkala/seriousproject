package com.ucmo.lms.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ucmo.lms.dao.MemberRepository;
import com.ucmo.lms.entity.LoanHistory;
import com.ucmo.lms.entity.Member;
import com.ucmo.lms.entity.ReserveQueue;
import com.ucmo.lms.service.ActionConclusion;
import com.ucmo.lms.service.ArtifactService;
import com.ucmo.lms.service.Common;
import com.ucmo.lms.service.LoanHistoryService;
import com.ucmo.lms.service.LoginService;
import com.ucmo.lms.service.MemberService;
import com.ucmo.lms.service.ReserveQueueService;

import org.springframework.security.core.Authentication;

@Controller
public class MemberController {
  @Autowired
  LoginService loginService;

  @Autowired
  MemberService memberService;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  ArtifactService artifactService;

  @Autowired
  LoanHistoryService LoanHistoryService;

  @Autowired
  ReserveQueueService reserveQueueService;

  @GetMapping("/member")
  public String member() {
    return "redirect:/member/dashboard";
  }

  @GetMapping("/member/dashboard")
  public String dashboard(Model model, Authentication authentication) {
    loginService.addMemberToModel(model, authentication);
    Member member = loginService.getMemberFromUserObject(authentication);
    List<LoanHistory> loans = LoanHistoryService.findByMember(member);
    List<ReserveQueue> reservedLoans = reserveQueueService.getReservedLoansForMember(member);
    List<LoanHistory> historicalLoans = LoanHistoryService.getReturnedOnLoans(member);
    // Page<LoanHistory> historicalLoans = LoanHistoryService.getHistorialLoans(member);
    List<LoanHistory> historicalLoansSubList;
    if (historicalLoans.size() > 3) {
      historicalLoansSubList = historicalLoans.subList(historicalLoans.size() - 3, historicalLoans.size());
    } else {
      historicalLoansSubList = historicalLoans;
    }

    model.addAttribute("loans", loans);
    model.addAttribute("historicalLoans", historicalLoansSubList);
    model.addAttribute("reservedLoans", reservedLoans);
    return "member/dashboard.html";
  }

  @GetMapping("/member/historical")
  public String historicalView(@RequestParam(defaultValue = "newest") String sortBy, Model model,
      Authentication authentication) {
    loginService.addMemberToModel(model, authentication);
    Member member = loginService.getMemberFromUserObject(authentication);
    // List<LoanHistory> historicalLoans = LoanHistoryService.getReturnedOnLoans(member);
    // model.addAttribute("historicalLoans", historicalLoans);
    List<List<LoanHistory>> list = LoanHistoryService.getIssuedOn(member, sortBy);
    model.addAttribute("lists", list);
    return "member/historical";
  }

  @GetMapping("/member/profile/view")
  public String profileView(Model model, Authentication authentication) {
    loginService.addMemberToModel(model, authentication);
    Member member = loginService.getMemberFromUserObject(authentication);
    model.addAttribute("bornOn", member.getBornOn() == null ? "" : member.getBornOn().format(Common.dateFormatter));
    model.addAttribute("joinedOn", member.getJoinedOn().format(Common.dateFormatter));
    model.addAttribute("lastActiveOn", member.getLastActiveOn().format(Common.dateFormatter));
    return "member/profile/view.html";
  }

  @GetMapping("/member/profile/edit")
  public String profileEditGet(Model model, Authentication authentication) {
    loginService.addMemberToModel(model, authentication);
    Member member = loginService.getMemberFromUserObject(authentication);
    model.addAttribute("bornOn", member.getBornOn() == null ? "" : member.getBornOn().format(Common.dateFormatter));
    model.addAttribute("joinedOn", member.getJoinedOn().format(Common.dateFormatter));
    model.addAttribute("lastActiveOn", member.getLastActiveOn().format(Common.dateFormatter));
    return "member/profile/edit.html";
  }

  @PostMapping("/member/profile/edit")
  public String profileEditPost(@RequestParam(defaultValue = "", required = false) String isSuccess,
      @RequestParam(defaultValue = "", required = false) String successMessage,
      @RequestParam(defaultValue = "", required = false) String failureMessage,
      @RequestParam(name = "id", defaultValue = "", required = false) String stringId,
      @RequestParam(name = "email", defaultValue = "", required = false) String email,
      @RequestParam(name = "fullName", defaultValue = "", required = false) String fullName,
      @RequestParam(name = "mobileNumber", defaultValue = "", required = false) String mobileNumber,
      @RequestParam(name = "address", defaultValue = "", required = false) String address,
      @RequestParam(name = "website", defaultValue = "", required = false) String website,
      @RequestParam(name = "bornOn", defaultValue = "", required = false) String bornOn,
      @RequestParam(name = "bio", required = false) String bio,
      @RequestParam(name = "type", defaultValue = "", required = false) String type,
      @RequestParam(name = "joinedOn", required = false) String joinedOn,
      @RequestParam(name = "lastActiveOn", required = false) String lastActiveOn, Model model,
      Authentication authentication) {
    Member member = loginService.getMemberFromUserObject(authentication);
    ActionConclusion actionConclusion = memberService.update(member.getId().toString(), email, fullName, mobileNumber,
        address, website, bornOn, bio, member.getType());
    loginService.addMemberToModel(model, authentication);
    model.addAttribute("bornOn", member.getBornOn() == null ? "" : member.getBornOn().format(Common.dateFormatter));
    model.addAttribute("joinedOn", member.getJoinedOn().format(Common.dateFormatter));
    model.addAttribute("lastActiveOn", member.getLastActiveOn().format(Common.dateFormatter));
    model.addAttribute("previousIsSuccess", actionConclusion.isSuccess.toString());
    model.addAttribute("previousSuccessMessage", actionConclusion.message);
    model.addAttribute("previousFailureMessage", actionConclusion.message);
    if (actionConclusion.isSuccess) {
      return "member/profile/view.html";
    } else {
      model.addAttribute("previousFullName", fullName);
      model.addAttribute("previousEmail", email);
      model.addAttribute("previousMobileNumber", mobileNumber);
      model.addAttribute("previousAddress", address);
      model.addAttribute("previousWebsite", website);
      model.addAttribute("previousBio", bio);
      model.addAttribute("previousType", type);
      return "member/profile/edit.html";
    }
  }

  @GetMapping("/member/renew")
  public String artifactRenew(@RequestParam(name = "id", required = true) Long id,
      @RequestParam(name = "days", value = "days") String days, Model model, Authentication authentication,
      RedirectAttributes redirectAttrs) {
    Member member = loginService.getMemberFromUserObject(authentication);
    ActionConclusion ac = LoanHistoryService.renew(Long.toString(id), days, member.isAdmin());
    loginService.addMemberToModel(model, authentication);

    redirectAttrs.addFlashAttribute("renewalFailed", ac.isSuccess);
    redirectAttrs.addFlashAttribute("renewal", true);
    redirectAttrs.addFlashAttribute("renewalMsg", ac.message);
    return "redirect:/member/dashboard";
  }

  @GetMapping("/member/reserve")
  public String artifactReserve(@RequestParam(name = "id", required = true) Long id,
      @RequestParam(name = "isbn") String isbn, Model model, Authentication authentication,
      RedirectAttributes redirectAttrs, HttpServletRequest request) throws URISyntaxException {
    ActionConclusion ac;
    if (!loginService.isAuthenticated(authentication)) {
      ac = new ActionConclusion(false, "Unable to reserve. Please log in to start reserving.");
    } else {
      Member member = loginService.getMemberFromUserObject(authentication);
      ac = reserveQueueService.create(isbn, Long.toString(member.getId()),
          LocalDate.now().plusDays(Common.DAYS_TILL_EXPIRED).toString());
      loginService.addMemberToModel(model, authentication);
    }
    redirectAttrs.addFlashAttribute("reserve", true);
    redirectAttrs.addFlashAttribute("reserveMsg", ac.message);
    redirectAttrs.addFlashAttribute("reserveFailed", ac.isSuccess);
    String relativePath = new URI(request.getHeader("referer")).getPath();
    String query = new URI(request.getHeader("referer")).getQuery();
    String redirectTo = query == null ? relativePath : relativePath + '?' + query;
    return "redirect:" + redirectTo;
  }

  @GetMapping("/member/reserve/remove")
  public String artifactReserveRemove(@RequestParam(name = "id", required = true) String loanID, Model model,
      Authentication authentication, RedirectAttributes redirectAttrs) {
    ActionConclusion ac = reserveQueueService.delete(loanID);
    loginService.addMemberToModel(model, authentication);
    redirectAttrs.addFlashAttribute("remove", true);
    redirectAttrs.addFlashAttribute("removeMsg", ac.message);
    redirectAttrs.addFlashAttribute("removeFailed", ac.isSuccess);
    return "redirect:/member/dashboard#reservations";
  }

  @GetMapping("/admin/members/view")
  public String membersView(@RequestParam(defaultValue = "1", required = false) Integer page,
      @RequestParam(defaultValue = "", required = false) String searchQuery,
      @RequestParam(defaultValue = "", required = false) String isSuccess,
      @RequestParam(defaultValue = "", required = false) String successMessage,
      @RequestParam(defaultValue = "", required = false) String failureMessage, Model model,
      Authentication authentication) {
    loginService.addMemberToModel(model, authentication);
    Page<Member> members = memberService.search(searchQuery, page - 1);
    model.addAttribute("totalEmptyRows", Common.getTotalEmptyRows(members.getNumberOfElements()));
    model.addAttribute("totalPages", members.getTotalPages());
    model.addAttribute("currentPage", page);
    model.addAttribute("members", members);

    model.addAttribute("previousQuery", searchQuery);
    model.addAttribute("previousIsSuccess", isSuccess);
    model.addAttribute("previousSuccessMessage", successMessage);
    model.addAttribute("previousFailureMessage", failureMessage);
    return "admin/member/view.html";
  }

  @GetMapping("/admin/members/edit")
  public String membersEdit(@RequestParam(name = "id") String stringId, Model model, Authentication authentication) {
    loginService.addMemberToModel(model, authentication);
    Member member = memberRepository.findById(Common.convertStringToLong(stringId)).get();
    model.addAttribute("member", member);
    model.addAttribute("joinedOn", member.getJoinedOn().format(Common.dateFormatter));
    model.addAttribute("lastActiveOn", member.getLastActiveOn().format(Common.dateFormatter));
    model.addAttribute("bornOn", member.getBornOn() == null ? "" : member.getBornOn().format(Common.dateFormatter));
    return "admin/member/edit.html";
  }

  @PostMapping("/admin/members/edit")
  public String membersEditPost(@RequestParam(defaultValue = "1", required = false) Integer page,
      @RequestParam(defaultValue = "", required = false) String searchQuery,
      @RequestParam(defaultValue = "", required = false) String isSuccess,
      @RequestParam(defaultValue = "", required = false) String successMessage,
      @RequestParam(defaultValue = "", required = false) String failureMessage,
      @RequestParam(name = "id", required = true) String stringId,
      @RequestParam(name = "email", defaultValue = "", required = false) String email,
      @RequestParam(name = "fullName", defaultValue = "", required = false) String fullName,
      @RequestParam(name = "mobileNumber", defaultValue = "", required = false) String mobileNumber,
      @RequestParam(name = "address", defaultValue = "", required = false) String address,
      @RequestParam(name = "website", defaultValue = "", required = false) String website,
      @RequestParam(name = "bornOn", defaultValue = "", required = false) String bornOn,
      @RequestParam(name = "bio", required = false) String bio,
      @RequestParam(name = "type", defaultValue = "member", required = false) String type,
      @RequestParam(name = "joinedOn", required = false) String joinedOn,
      @RequestParam(name = "lastActiveOn", required = false) String lastActiveOn, Model model,
      Authentication authentication) {
    loginService.addMemberToModel(model, authentication);
    ActionConclusion actionConclusion = memberService.update(stringId, email, fullName, mobileNumber, address, website,
        bornOn, bio, type);
    model.addAttribute("previousIsSuccess", actionConclusion.isSuccess.toString());
    model.addAttribute("previousSuccessMessage", actionConclusion.message);
    model.addAttribute("previousFailureMessage", actionConclusion.message);
    if (actionConclusion.isSuccess) {
      Page<Member> members = memberService.search("", page - 1);

      model.addAttribute("totalEmptyRows", Common.getTotalEmptyRows(members.getNumberOfElements()));
      model.addAttribute("totalPages", members.getTotalPages());
      model.addAttribute("currentPage", page);
      model.addAttribute("members", members);

      model.addAttribute("previousQuery", "");
      return "admin/member/view.html";
    } else {
      Member member = memberRepository.findById(Common.convertStringToLong(stringId)).get();
      model.addAttribute("member", member);
      model.addAttribute("previousFullName", fullName);
      model.addAttribute("previousEmail", email);
      model.addAttribute("previousMobileNumber", mobileNumber);
      model.addAttribute("previousAddress", address);
      model.addAttribute("previousWebsite", website);
      model.addAttribute("previousBio", bio);
      model.addAttribute("previousType", type);
      return "admin/member/edit.html";
    }
  }

  @PostMapping("/admin/members/delete")
  @ResponseBody
  public ActionConclusion membersDelete(@RequestParam(name = "id") String stringId, Authentication authentication) {
    Member member = loginService.getMemberFromUserObject(authentication);
    if (member != null && stringId.equals(member.getId().toString())) {
      return new ActionConclusion(false, "Failed to delete. You cannot delete yourself.");
    }
    return memberService.delete(stringId);
  }

  @GetMapping("/members/search")
  @ResponseBody
  public Page<Member> membersSearch(@RequestParam(defaultValue = "", required = false) String searchQuery) {
    return memberService.search(searchQuery, 0, Common.QUICK_SEARCH_ROWS);
  }
}
