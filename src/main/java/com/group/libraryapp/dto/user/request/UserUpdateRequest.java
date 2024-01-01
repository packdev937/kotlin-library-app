package com.group.libraryapp.dto.user.request;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class UserUpdateRequest {

    @Nullable
    private long id;
    private String name;

    public UserUpdateRequest(long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Nullable
    public long getId() {
        return id;
    }

    @NotNull
    public String getName() {
        return name;
    }

}
