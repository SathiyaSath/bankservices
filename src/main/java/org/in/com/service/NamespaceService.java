package org.in.com.service;

import java.util.List;

import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;

public interface NamespaceService {

	public void updateNamespace(AuthDTO authDTO, NamespaceDTO namespaceDTO);

	List<NamespaceDTO> getAllNamespaceDetails(AuthDTO authDTO, NamespaceDTO namespaceDto);

	public NamespaceDTO getByCode(AuthDTO authDTO, NamespaceDTO namespaceDTO);
}
