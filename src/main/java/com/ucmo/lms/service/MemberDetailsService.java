package com.ucmo.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ucmo.lms.dao.MemberRepository;
import com.ucmo.lms.entity.Member;

import java.util.Optional;

@Service
public class MemberDetailsService implements UserDetailsService {
    @Autowired
    MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Member> member = memberRepository.findByEmailOptional(email);

        if (member == null) {
            throw new UsernameNotFoundException("Not found: " + email);
        }

        return toUserDetails(member.get());
    }

    public UserDetails toUserDetails(Member member) {
        return User.withUsername(member.getEmail()).password(member.getLogin().getHash()).roles(member.getRoles())
                .build();
    }
}
