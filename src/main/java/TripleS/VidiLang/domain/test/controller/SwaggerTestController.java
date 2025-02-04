package TripleS.VidiLang.domain.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RestController
@RequestMapping("/")
public class SwaggerTestController {

    @Operation(summary = "test API", description = "test API")
    @GetMapping("string")
    public String returnStr(){
        return "Hello World";
    }

}
