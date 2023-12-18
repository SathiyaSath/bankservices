package org.in.com.service.impl;

import java.util.List;

import org.in.com.dao.NamespaceDAO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;

import org.in.com.service.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class NamespaceServiceImpl implements NamespaceService {

	@Autowired
	private NamespaceDAO namespaceDAO;

	@Override
	public void updateNamespace(AuthDTO authDTO, NamespaceDTO namespaceDTO) {
		try {
			namespaceDAO.updateNamespace(authDTO, namespaceDTO);
		}
		catch (Exception e) {
			throw new RuntimeException("An error occurred while getting namespace", e);
		}
	}

	@Override
	public List<NamespaceDTO> getAllNamespaceDetails(AuthDTO authDTO, NamespaceDTO namespaceDTO) {
		try {
			return namespaceDAO.getAllNamespaceDetails(authDTO, namespaceDTO);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("An error occurred while getting namespaces.", e);
		}
	}

	@Override
	public NamespaceDTO getByCode(AuthDTO authDTO, NamespaceDTO namespaceDTO) {
		try {
			namespaceDAO.getByCode(authDTO, namespaceDTO);
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("An error occurred while getting namespaces.", e);
		}
		return namespaceDTO;
	}

}
