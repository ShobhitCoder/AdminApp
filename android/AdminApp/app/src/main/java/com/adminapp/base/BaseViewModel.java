package com.adminapp.base;

import android.arch.lifecycle.ViewModel;
import android.databinding.ObservableBoolean;

import com.adminapp.utils.PrefManager;


public abstract class BaseViewModel<N> extends ViewModel {

    private final ObservableBoolean mIsLoading = new ObservableBoolean(false);

    private N mNavigator;



    public PrefManager prefrenceManager;

    @Override
    protected void onCleared() {
        super.onCleared();
    }

    public ObservableBoolean getIsLoading() {
        return mIsLoading;
    }

    public void setIsLoading(boolean isLoading) {
        mIsLoading.set(isLoading);
    }

    public N getNavigator() {
        return mNavigator;
    }

    public void setNavigator(N navigator) {
        this.mNavigator = navigator;
    }

    public void setPrefrenceManager(PrefManager prefrenceManager) {
        this.prefrenceManager = prefrenceManager;
    }

    public PrefManager getPrefrenceManager() {
        return prefrenceManager;
    }
}
