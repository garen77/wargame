package com.wg.assembler;

public interface IAssembler<MOD, DTO> {

	public void toDto(MOD model, DTO dto);
	
	public void fromDto(MOD model, DTO dto);
}
