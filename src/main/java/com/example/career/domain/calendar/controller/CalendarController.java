package com.example.career.domain.calendar.controller;

import com.example.career.domain.calendar.dto.CalendarMentorRespDto;
import com.example.career.domain.calendar.dto.CalendarRegistReqDto;
import com.example.career.domain.calendar.service.CalendarService;
import com.example.career.domain.consult.Dto.CalendarDenyReqDto;
import com.example.career.domain.consult.Entity.Consult;
import com.example.career.global.annotation.Authenticated;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("calendar")
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping("mentor/view")
    public ResponseEntity<CalendarMentorRespDto> getCalendarByMentorId(@RequestParam Long mentorId) {

        return new ResponseEntity<>(calendarService.getMentorCalendar(mentorId), HttpStatus.OK);
    }

    // 상담 거절 - 멘토가
    @Authenticated
    @PostMapping("mentor/deny")
    public ResponseEntity<Consult> DenyConsultByMentor(@RequestBody CalendarDenyReqDto calendarDenyReqDto, HttpServletRequest request) {
        Long userId = (Long) request.getAttribute("userId");
        int validCheck = userId.compareTo(calendarDenyReqDto.getId()); // 같으면 0 틀리면 1
        Consult consult = calendarService.denyConsultByMentor(calendarDenyReqDto);

        // JWT의 ID와 Parameter ID 값이 같은지 확인
        if(validCheck == 0 && consult != null) {
            return new ResponseEntity<>(consult, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }
    // 상담 수락 - 멘토가
    @Authenticated
    @PostMapping("mentor/accept")
    public ResponseEntity<Consult> AcceptConsultByMentor(@RequestBody CalendarDenyReqDto calendarDenyReqDto, HttpServletRequest request) throws IOException {
        String username = (String) request.getAttribute("subject");
        Consult consult = calendarService.AcceptConsultByMentor(calendarDenyReqDto, username);

        return new ResponseEntity<>(consult, HttpStatus.OK);

    }

    // 상담 신청 - 멘티가
    @PostMapping("mentee/register")
    public ResponseEntity<Consult> RegisterConsultByMentee(@RequestBody CalendarRegistReqDto calendarRegistReqDto) {

        return new ResponseEntity<>(calendarService.RegisterConsultByMentee(calendarRegistReqDto), HttpStatus.OK);
    }

}
