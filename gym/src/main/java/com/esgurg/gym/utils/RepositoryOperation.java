package com.esgurg.gym.utils;

@FunctionalInterface
public interface RepositoryOperation {
    // Try to execute a function that returns an object in a rest controller context
    Object execute();
}
