package com.anjbo.service.tools.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.anjbo.bean.tools.Booking;
import com.anjbo.bean.tools.BookingDetail;
import com.anjbo.dao.tools.BookingMapper;
import com.anjbo.service.tools.BookingService;
/**
 * 预约取号
 * @author limh limh@zxsf360.com
 * @date 2016-6-2 下午07:17:55
 */
@Service
public class BookingServiceImpl implements BookingService{

	@Resource
	public BookingMapper bookingMapper;

	@Override
	public List<Booking> selectBookingList(Booking booking) {
		return bookingMapper.selectBookingList(booking);
	}

	@Override
	public int selectBookingCount(Booking booking) {
		return bookingMapper.selectBookingCount(booking);
	}

	@Override
	public Integer bookingExit(Booking booking) {
		return bookingMapper.bookingExit(booking);
	}

	@Override
	public int addBooking(Booking booking) {
		return bookingMapper.addBooking(booking);
	}

	@Override
	public List<BookingDetail> selectBookingDetailList(BookingDetail bookingDetail) {
		return bookingMapper.selectBookingDetailList(bookingDetail);
	}

	@Override
	public int selectBookingDetailCount(BookingDetail bookingDetail) {
		return bookingMapper.selectBookingDetailCount(bookingDetail);
	}

	@Override
	public int addBookingDetail(BookingDetail bookingDetail) {
		return bookingMapper.addBookingDetail(bookingDetail);
	}


	@Override
	public int updateBookingCode(BookingDetail bookingDetail) {
		return bookingMapper.updateBookingCode(bookingDetail);
	}

	@Override
	public int updateBookingDetailStatus(BookingDetail bookingDetail) {
		return bookingMapper.updateBookingDetailStatus(bookingDetail);
	}

	@Override
	public BookingDetail bookingDetailExit(BookingDetail bookingDetail) {
		return bookingMapper.bookingDetailExit(bookingDetail);
	}

	@Override
	public int deleteBookingDetail(int id) {
		return bookingMapper.deleteBookingDetail(id);
	}

	@Override
	public BookingDetail selectBookingDetail(int id) {
		return bookingMapper.selectBookingDetail(id);
	}

	@Override
	public int updateBookingDetail(BookingDetail bookingDetail) {
		return bookingMapper.updateBookingDetail(bookingDetail);
	}

	@Override
	public void deleteBookingDetails(String uid, int[] detailIds) {
		for(int id : detailIds){
			BookingDetail bookingDetail = bookingMapper.selectBookingDetail(id);
			if(bookingDetail!=null&&bookingDetail.getUid().equals(uid)){
				bookingMapper.deleteBookingDetail(id);
			}
		}
	}

	@Override
	public int updateBookingBase(Map<String, Object> param) {
		return bookingMapper.updateBookingBase(param);
	}
}
