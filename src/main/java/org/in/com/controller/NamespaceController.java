package org.in.com.controller;

import java.util.ArrayList;
import java.util.List;
import org.in.com.controller.io.NamespaceIO;
import org.in.com.dto.AuthDTO;
import org.in.com.dto.NamespaceDTO;
import org.in.com.service.AuthService;
import org.in.com.service.NamespaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/{authtoken}/namespace")
public class NamespaceController {

	@Autowired
	NamespaceService namespaceService;
	@Autowired
	AuthService authService;

	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public NamespaceIO updateNamespace(@RequestBody NamespaceIO namespaceIO, @PathVariable("authtoken") String authtoken) throws Exception {
		NamespaceIO namespaceio = new NamespaceIO();
		NamespaceDTO namespaceDTO = new NamespaceDTO();
		AuthDTO auth = authService.getAuth(authtoken);
		namespaceDTO.setName(namespaceIO.getName());
		namespaceDTO.setAddress(namespaceIO.getAddress());
		namespaceDTO.setActiveFlag(namespaceIO.getActiveFlag());
		namespaceService.updateNamespace(auth, namespaceDTO);
		namespaceio.setActiveFlag(namespaceDTO.getActiveFlag());
		namespaceio.setCode(namespaceDTO.getCode());
		return namespaceio;
	}

	@RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public List<NamespaceIO> getAllNamespaceDetails(@PathVariable("authtoken") String authtoken) throws Exception {
		List<NamespaceIO> namespaces = new ArrayList<NamespaceIO>();
		AuthDTO auth = authService.getAuth(authtoken);
		NamespaceDTO namespaceDto = new NamespaceDTO();
		List<NamespaceDTO> namespacesDtolist = namespaceService.getAllNamespaceDetails(auth, namespaceDto);
		for (NamespaceDTO namespaceList : namespacesDtolist) {
			NamespaceIO namespaceIO = new NamespaceIO();
			namespaceIO.setCode(namespaceList.getCode());
			namespaceIO.setName(namespaceList.getName());
			namespaceIO.setAddress(namespaceList.getAddress());
			namespaceIO.setActiveFlag(namespaceList.getActiveFlag());
			namespaces.add(namespaceIO);
		}
		return namespaces;
	}

	@RequestMapping(value = "/getNamespaceDetails", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public NamespaceIO getByCode(@PathVariable("authtoken") String authtoken) throws Exception {
		AuthDTO auth = authService.getAuth(authtoken);
		NamespaceDTO namespaceDTO = new NamespaceDTO();
		NamespaceDTO namespacesData = namespaceService.getByCode(auth, namespaceDTO);
		NamespaceIO namespacesIO = new NamespaceIO();
		namespacesIO.setName(namespacesData.getName());
		namespacesIO.setAddress(namespacesData.getAddress());
		namespacesIO.setActiveFlag(namespacesData.getActiveFlag());
		return namespacesIO;
	}
}
