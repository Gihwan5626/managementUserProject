package com.example.managementuser.service;

import com.example.managementuser.entity.NoticeEntity;
import com.example.managementuser.repository.NoticeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeService {

	@Autowired
	private NoticeRepository noticeRepository;

	public List<NoticeEntity> getAllNotice() {
		
		List<NoticeEntity> notices =  noticeRepository.findAll();

		return notices;
	}
	
	public NoticeEntity getByNoticeId(long noticeID) {
		return noticeRepository.findById(noticeID).orElse(null);
		
	}

	public void insertNotice(NoticeEntity noticeEntity) {
		noticeRepository.save(noticeEntity);
	}


}
