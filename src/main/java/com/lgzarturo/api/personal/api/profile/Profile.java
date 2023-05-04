package com.lgzarturo.api.personal.api.profile;

import com.lgzarturo.api.personal.api.address.Address;
import com.lgzarturo.api.personal.api.candidate.Candidate;
import com.lgzarturo.api.personal.api.experience.Experience;
import com.lgzarturo.api.personal.api.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.jpa.domain.AbstractAuditable;

import java.time.LocalDate;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Entity(name="profiles")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Profile extends AbstractAuditable<User, Long> {
    private UUID uuid = UUID.randomUUID();
    @Column(length = 80)
    private String firstName;
    @Column(length = 80)
    private String lastName;
    @Column(length = 160)
    private String profession;
    @Column(length = 240)
    private String resume;
    @ManyToOne
    @JoinColumn(name="address_id")
    private Address address;
    private LocalDate birthDate;
    @Column(length = 40)
    private String birthPlace;
    private Integer children;
    @Column(length = 16)
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column(length = 40)
    private String nationality;
    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private MaritalStatus maritalStatus;
    @ElementCollection
    @CollectionTable(name="profile_phone_numbers", joinColumns=@JoinColumn(name="profile_id"))
    private Set<String> phoneNumbers = new HashSet<>();
    @ElementCollection
    @CollectionTable(name="profile_websites", joinColumns=@JoinColumn(name="profile_id"))
    private Set<String> websites = new HashSet<>();
    @ElementCollection
    @CollectionTable(name="profile_emails", joinColumns=@JoinColumn(name="profile_id"))
    private Set<String> emails = new HashSet<>();
    @ElementCollection
    @CollectionTable(name="profile_skills", joinColumns=@JoinColumn(name="profile_id"))
    private Set<String> skills = new HashSet<>();
    @ElementCollection
    @CollectionTable(name="profile_interests", joinColumns=@JoinColumn(name="profile_id"))
    private Set<String> interests = new HashSet<>();
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="profile_languages", joinColumns=@JoinColumn(name="profile_id"))
    private Map<Language, Integer> languages = new HashMap<>();
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="profile_social_networks", joinColumns=@JoinColumn(name="profile_id"))
    private Map<SocialNetwork, String> socialNetworks = new HashMap<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Experience> experiences = new HashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Candidate> candidates = new HashSet<>();
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;
}
