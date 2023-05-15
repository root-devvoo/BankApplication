package shop.mtcoding.bank.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import shop.mtcoding.bank.config.auth.LoginUser;
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.dto.transaction.TransactionRespDto.TransactionListRespDto;
import shop.mtcoding.bank.service.TransactionService;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class TransactionController {
    private final TransactionService transactionService;

    @ApiOperation(value = "입/출금목록 보기", notes = "입/출금목록 보기")
    @GetMapping("/s/account/{number}/transaction")
    public ResponseEntity<?> findTransactionList(@PathVariable Long number,
            @RequestParam(value = "gubun", defaultValue = "ALL") String gubun,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @AuthenticationPrincipal LoginUser loginUser) {
        TransactionListRespDto transactionListRespDto = transactionService.입출금목록보기(loginUser.getUser().getId(), number,
                gubun, page);
        // return new ResponseEntity<>(new ResponseDto<>(1, "입출금목록보기 성공",
        // transactionListRespDto), HttpStatus.OK);
        return ResponseEntity.ok().body(new ResponseDto<>(1, "입출금목록보기 성공", transactionListRespDto));

    }
}
