package com.example.festival.partner.repository;

import com.example.festival.auth.repository.AuthRepository;
import com.example.festival.partner.dto.PartnerDto;
import com.example.festival.partner.entity.Partner;
import com.example.festival.user.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Iterator;
import java.util.Optional;

@Repository
public class PartnerRepository{
    private final PartnerRepositoryInterface partnerRepositoryInterface;
    private final AuthRepository authRepository;

    public PartnerRepository(@Autowired PartnerRepositoryInterface partnerRepositoryInterface,
                             @Autowired AuthRepository authRepository) {
        this.partnerRepositoryInterface = partnerRepositoryInterface;
        this.authRepository = authRepository;
    }

    public void partnerCreate(String identify, PartnerDto partnerDto) {
        User user = authRepository.findByIdentify(identify);

        Partner partner = new Partner();
        BeanUtils.copyProperties(partnerDto, partner);
        partner.setUser(user); //작성자 저장
        this.partnerRepositoryInterface.save(partner);
    }

    public Partner partnerRead(Long partnerId) {
        Optional<Partner> partner = this.partnerRepositoryInterface.findById(partnerId);
        return partner.get();
    }

    public Iterator<Partner> partnerReadAll() {
        return this.partnerRepositoryInterface.findAll().iterator();
    }

    public void partnerUpdate(Long partnerId, PartnerDto partnerDto) {
        Partner partner = this.partnerRepositoryInterface.findById(partnerId).get();

        if(partnerDto.getTitle() != null) {
            partner.setTitle(partnerDto.getTitle());
        }
        if(partnerDto.getContent() != null) {
            partner.setContent(partnerDto.getContent());
        }
        this.partnerRepositoryInterface.save(partner);
    }

    public void partnerDelete(Long partnerId) {
        this.partnerRepositoryInterface.deleteById(partnerId);
    }
}
