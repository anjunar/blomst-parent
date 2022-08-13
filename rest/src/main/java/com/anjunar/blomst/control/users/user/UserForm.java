package com.anjunar.blomst.control.users.user;

import com.anjunar.common.filedisk.Base64Resource;
import com.anjunar.common.filedisk.FileDiskUtils;
import com.anjunar.common.filedisk.Image;
import com.anjunar.common.rest.api.AbstractRestEntity;
import com.anjunar.common.rest.api.AbstractRestEntityConverter;
import com.anjunar.common.rest.api.ImageType;
import com.anjunar.common.rest.objectmapper.Converter;
import com.anjunar.common.rest.objectmapper.Mapper;
import com.anjunar.common.rest.schema.annotations.JsonSchema;
import com.anjunar.common.rest.schema.schema.JsonNode;
import com.anjunar.common.security.EmailType;
import com.anjunar.common.security.IdentityProvider;
import com.anjunar.common.security.Role;
import com.anjunar.common.security.User;
import com.anjunar.blomst.control.roles.role.RoleForm;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NaturalId
@JsonSchema(widget = JsonNode.Widget.FORM)
public class UserForm extends AbstractRestEntity {

    @NotBlank
    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "First Name", naming = true)
    private String firstName;

    @NotBlank
    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.TEXT, title = "Last Name", naming = true)
    private String lastName;

    @NotNull
    @JsonSchema(widget = JsonNode.Widget.DATE, title = "Birthdate")
    private LocalDate birthDate;

    @NotBlank
    @Size(min = 3, max = 80)
    @JsonSchema(widget = JsonNode.Widget.PASSWORD, title = "Password")
    private String password;

    @JsonSchema(widget = JsonNode.Widget.IMAGE, title = "Picture")
    @Mapper(ImageConverter.class)
    private ImageType picture = new ImageType();

    @JsonSchema(widget = JsonNode.Widget.REPEAT, title = "Emails")
    private List<EmailForm> emails = new ArrayList<>();

    @JsonSchema(widget = JsonNode.Widget.CHECKBOX, title = "Enabled")
    private boolean enabled;

    @JsonSchema(widget = JsonNode.Widget.LAZY_MULTI_SELECT, title = "Roles")
    @Size(min = 1)
    @NotNull
    private Set<RoleForm> roles = new HashSet<>();

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ImageType getPicture() {
        return picture;
    }

    public void setPicture(ImageType picture) {
        this.picture = picture;
    }

    public List<EmailForm> getEmails() {
        return emails;
    }

    public void setEmails(List<EmailForm> emails) {
        this.emails = emails;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<RoleForm> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleForm> roles) {
        this.roles = roles;
    }

    public static class ImageConverter implements Converter<Image, ImageType> {

        @Override
        public ImageType factory(Image harddiskFile) {
            if (harddiskFile == null) {
                return new ImageType();
            }
            ImageType image = new ImageType();
            image.setId(harddiskFile.getId());
            image.setName(harddiskFile.getName());
            image.setLastModified(harddiskFile.getLastModified());
            image.setData(FileDiskUtils.buildBase64(harddiskFile.getType(), harddiskFile.getSubType(), harddiskFile.getData()));
            return image;
        }

        @Override
        public Image updater(Image image, ImageType imageType) {
            if (image == null) {
                return null;
            } else {
                Base64Resource process = FileDiskUtils.extractBase64(imageType.getData());

                image.setData(process.getData());
                image.setType(process.getType());
                image.setSubType(process.getSubType());

                image.setName(imageType.getName());
                image.setLastModified(imageType.getLastModified());
            }
            return image;
        }
    }

    public static class UserFormConverter extends AbstractRestEntityConverter<User, UserForm> {

        public static final UserFormConverter INSTANCE = new UserFormConverter();

        @Override
        public UserForm factory(UserForm form, User user) {
            form.setId(user.getId());
            form.setFirstName(user.getFirstName());
            form.setLastName(user.getLastName());
            form.setBirthDate(user.getBirthDate());
            form.setPassword("s3cr3t");
            for (EmailType email : user.getEmails()) {
                form.getEmails().add(EmailForm.factory(email));
            }
            form.setEnabled(user.isEnabled());
            form.setPicture(ImageType.factory(user.getPicture()));
            Set<RoleForm> roleForms = new HashSet<>();
            for (Role role : user.getRoles()) {
                roleForms.add(RoleForm.factory(role));
            }
            form.setRoles(roleForms);
            return super.factory(form, user);
        }

        @Override
        public User updater(UserForm resource, User user, EntityManager entityManager, IdentityProvider identityProvider) {
            user.setFirstName(resource.getFirstName());
            user.setLastName(resource.getLastName());
            user.setBirthDate(resource.getBirthDate());
            user.setEnabled(resource.isEnabled());

            user.getEmails().clear();
            for (EmailForm emailForm : resource.getEmails()) {
                user.getEmails().add(EmailForm.updater(emailForm, new EmailType(), entityManager, identityProvider));
            }

            if (user.getPicture() == null) {
                user.setPicture(ImageType.updater(resource.getPicture(), new Image()));
            } else {
                user.setPicture(ImageType.updater(resource.getPicture(), user.getPicture()));
            }

            Set<RoleForm> roleForms = resource.getRoles();
            if (identityProvider.hasRole("Administrator")) {
                user.getRoles().clear();
                for (RoleForm roleForm : roleForms) {
                    Role role = entityManager.find(Role.class, roleForm.getId());
                    user.getRoles().add(role);
                }
            }

            return user;
        }
    }

    public static UserForm factory(User user) {
        return UserFormConverter.INSTANCE.factory(new UserForm(), user);
    }

    public static User updater(UserForm resource, User entity, EntityManager entityManager, IdentityProvider identityProvider) {
        return UserFormConverter.INSTANCE.updater(resource, entity, entityManager, identityProvider);
    }


}
