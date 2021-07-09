package com.arounders.web.api;

import com.arounders.web.dto.BoardDTO;
import com.arounders.web.entity.Member;
import com.arounders.web.service.BoardService;
import com.arounders.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/admin/api/v1")
@RequiredArgsConstructor
public class AdminApiController {

    private final MemberService memberService;
    private final BoardService boardService;

    /* 회원 목록 조회 */
    @GetMapping("/members/{page}")
    public ResponseEntity<List<Member>> getMembers(@PathVariable("page") int page, String field, String keyword){

        List<Member> members = memberService.getMembers(page, field, keyword);

        return new ResponseEntity<>(members, HttpStatus.OK);
    }
    /* 회원 권한 변경 */
    @PostMapping(value = "/members")
    public ResponseEntity<Long> changeRole(@RequestBody Member member){

        System.out.println("member = " + member);
        Long result = memberService.update(member);

        System.out.println("result = " + result);

        return result != null? new ResponseEntity<>(result, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /* 가입한 회원 수 */
    @GetMapping(value = "/members/valid")
    public ResponseEntity<Integer> getValidMemberCount(){
        return new ResponseEntity<>(memberService.getValidMemberCount(), HttpStatus.OK);
    }
    /* 탈퇴한 회원 수 */
    @GetMapping(value = "/members/invalid")
    public ResponseEntity<Integer> getInvalidMemberCount(){
        return new ResponseEntity<>(memberService.getInvalidMemberCount(), HttpStatus.OK);
    }
    /* 오늘 가입한 회원 수 */
    @GetMapping(value = "/members/today")
    public ResponseEntity<Integer> getTodayMemberCount(){
        return new ResponseEntity<>(memberService.getTodayMemberCount(), HttpStatus.OK);
    }

    /* 게시글 조회 */
    @GetMapping(value = "/boards")
    public ResponseEntity<List<BoardDTO>> getAllBoards(@RequestParam( value = "page", defaultValue = "1") int page){
        return new ResponseEntity<>(boardService.getAllBoards(page), HttpStatus.OK);
    }
    /* 진행중 글 수 */
    @GetMapping(value = "/boards/process")
    public ResponseEntity<Integer> getProcessBoardCount(){
        return new ResponseEntity<>(boardService.getCountProcess(), HttpStatus.OK);
    }
    /* 완료된 글 수 */
    @GetMapping(value = "/boards/finish")
    public ResponseEntity<Integer> getFinishBoardCount(){
        return new ResponseEntity<>(boardService.getCountFinish(), HttpStatus.OK);
    }
    /* 오늘 작성한 글 수 */
    @GetMapping(value = "/boards/today")
    public ResponseEntity<Integer> getTodayBoardCount(){
        return new ResponseEntity<>(boardService.getCountToday(), HttpStatus.OK);
    }
}
