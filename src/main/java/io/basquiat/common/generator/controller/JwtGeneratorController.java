package io.basquiat.common.generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.basquiat.common.generator.domain.RequestQuery;
import io.basquiat.common.generator.domain.response.TokenInformation;
import io.basquiat.common.generator.service.GenerateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Mono;

/**
 * 
 * accessKey, secretKey로 jwt 생성하는 컨트롤
 * 
 * created by basquiat
 *
 */
@RestController
@RequestMapping("/api")
@Api(value = "Jwt Generator Controller", tags = {"Upbit Jwt Generator Controller"})
public class JwtGeneratorController {

	@Autowired
	private GenerateService generateService;
	
	/**
	 * 
	 * jwt 생성 요청하기
	 * 
	 * @param exchangeQuery
	 * @return Mono<TokenInformation>
	 */
	@ApiOperation(value = "Jwt 생성하기")
	@PostMapping("/jwt/gen")
	public Mono<TokenInformation> jwtGenerate(@RequestBody RequestQuery reqeustQuery) {
		return generateService.jwtGenerate(reqeustQuery);
	}

}
