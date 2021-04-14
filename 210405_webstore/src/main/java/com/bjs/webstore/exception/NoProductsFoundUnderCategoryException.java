package com.bjs.webstore.exception;

import java.io.Serializable;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND,
reason="이 범주에 속하는 상품은 없습니다.")	//not_found에 다른메소드를 넣어서 오류메시지를 나타낼수있음
public class NoProductsFoundUnderCategoryException 
extends RuntimeException implements Serializable{
	//Seializable을 임포트해야 이 클래스가 빈으로 자동 등록됨(generate~~~뭐시기로 해야함)
	/**
	 * 
	 */
	private static final long serialVersionUID = 3003647581548814541L;

}
