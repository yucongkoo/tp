package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.logic.commands.TagCommand.UpdatePersonTagsDescriptor;
import seedu.address.model.person.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building UpdatePersonTagsDescriptor objects.
 */
public class UpdatePersonTagsDescriptorBuilder {
    private UpdatePersonTagsDescriptor updatePersonTagsDescriptor;

    public UpdatePersonTagsDescriptorBuilder() {
        this.updatePersonTagsDescriptor = new UpdatePersonTagsDescriptor(new HashSet<>(), new HashSet<>());
    }

    public UpdatePersonTagsDescriptorBuilder(UpdatePersonTagsDescriptor updatePersonTagsDescriptor) {
        this.updatePersonTagsDescriptor = new UpdatePersonTagsDescriptor(updatePersonTagsDescriptor);
    }

    /**
     * Sets the {@code tagsToAdd} of the {@code EditPersonDescriptor} that we are building.
     */
    public UpdatePersonTagsDescriptorBuilder withTagsToAdd(String... tags) {
        Set<Tag> tagsToAdd = SampleDataUtil.getTagSet(tags);
        updatePersonTagsDescriptor.setTagsToAdd(tagsToAdd);
        return this;
    }

    /**
     * Sets the {@code tagsToDelete} of the {@code EditPersonDescriptor} that we are building.
     */
    public UpdatePersonTagsDescriptorBuilder withTagsToDelete(String... tags) {
        Set<Tag> tagsToDelete = SampleDataUtil.getTagSet(tags);
        updatePersonTagsDescriptor.setTagsToDelete(tagsToDelete);
        return this;
    }

    /**
     * Sets the {@code tagsToAdd} of the {@code EditPersonDescriptor} that we are building
     * to tags with of value 1, 2, 3, ..., n.
     */
    public UpdatePersonTagsDescriptorBuilder withEnumeratedTagsToAdd(int n) {
        Set<Tag> tagsToAdd = new HashSet<>();
        for (int i = 1; i <= n; ++i) {
            tagsToAdd.add(new Tag(Integer.toString(i)));
        }
        updatePersonTagsDescriptor.setTagsToAdd(tagsToAdd);
        return this;
    }

    /**
     * Sets the {@code tagsToDelete} of the {@code EditPersonDescriptor} that we are building
     * to tags with of value 1, 2, 3, ..., n.
     */
    public UpdatePersonTagsDescriptorBuilder withEnumeratedTagsToDelete(int n) {
        Set<Tag> tagsToDelete = new HashSet<>();
        for (int i = 1; i <= n; ++i) {
            tagsToDelete.add(new Tag(Integer.toString(i)));
        }
        updatePersonTagsDescriptor.setTagsToAdd(tagsToDelete);
        return this;
    }


    public UpdatePersonTagsDescriptor build() {
        return updatePersonTagsDescriptor;
    }
}
