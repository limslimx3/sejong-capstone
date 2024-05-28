package com.sejong.capstone.domain;

import com.sejong.capstone.domain.etc.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter(AccessLevel.PRIVATE)
public class Channel extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Long id;

    @Column(name = "channel", nullable = false)
    private String channelName;

    private int likes;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
        member.setChannel(this);
    }

    public static Channel createChannel(String channelName, Member member) {
        Channel channel = new Channel();
        channel.setChannelName(channelName);
        channel.setMember(member);
        return channel;
    }

}
