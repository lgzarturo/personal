package com.lgzarturo.api.personal.api.profile;

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
    @Column(length = 80)
    private String firstName;
    @Column(length = 80)
    private String lastName;
    @Column(length = 160)
    private String profession;
    @Column(length = 240)
    private String resume;
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
    @CollectionTable(name="profiles_phone_numbers", joinColumns=@JoinColumn(name="profile_id"))
    private Set<String> phoneNumbers;
    @ElementCollection
    @CollectionTable(name="profiles_websites", joinColumns=@JoinColumn(name="profile_id"))
    private Set<String> websites;
    @ElementCollection
    @CollectionTable(name="profiles_emails", joinColumns=@JoinColumn(name="profile_id"))
    private Set<String> emails;
    @ElementCollection
    @CollectionTable(name="profiles_skills", joinColumns=@JoinColumn(name="profile_id"))
    private Set<String> skills;
    @ElementCollection
    @CollectionTable(name="profiles_interests", joinColumns=@JoinColumn(name="profile_id"))
    private Set<String> interests;
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="profiles_languages", joinColumns=@JoinColumn(name="profile_id"))
    private Map<Language, Integer> languages;
    @ElementCollection
    @MapKeyColumn(name="name")
    @Column(name="value")
    @CollectionTable(name="profiles_social_networks", joinColumns=@JoinColumn(name="profile_id"))
    private Map<SocialNetwork, String> socialNetworks;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL)
    private Set<Experience> experiences;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProfileJob> profileJobs;
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    public void addPhoneNumber(String phoneNumber) {
        if (Objects.isNull(phoneNumbers)) phoneNumbers = new HashSet<>();
        phoneNumbers.add(phoneNumber);
    }

    public void addWebsite(String website) {
        if (Objects.isNull(websites)) websites = new HashSet<>();
        websites.add(website);
    }

    public void addEmail(String email) {
        if (Objects.isNull(emails)) emails = new HashSet<>();
        emails.add(email);
    }

    public void addSkill(String skill) {
        if (Objects.isNull(skills)) skills = new HashSet<>();
        skills.add(skill);
    }

    public void addInterest(String interest) {
        if (Objects.isNull(interests)) interests = new HashSet<>();
        interests.add(interest);
    }

    public void addLanguage(Language language, Integer level) {
        if (Objects.isNull(languages)) languages = new HashMap<>();
        languages.put(language, level);
    }

    public void addSocialNetwork(SocialNetwork socialNetwork, String url) {
        if (Objects.isNull(socialNetworks)) socialNetworks = new HashMap<>();
        socialNetworks.put(socialNetwork, url);
    }

    public void addExperience(Experience experience) {
        if (Objects.isNull(experiences)) experiences = new HashSet<>();
        experiences.add(experience);
        experience.setProfile(this);
    }
}
