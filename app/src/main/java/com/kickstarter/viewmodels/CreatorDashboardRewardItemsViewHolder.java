package com.kickstarter.viewmodels;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.kickstarter.ui.viewholders.KSViewHolder;

public class CreatorDashboardRewardItemsViewHolder extends KSViewHolder {

  private final CreatorDashboardRewardItemsHolderViewModel.ViewModel viewModel;

  public CreatorDashboardRewardItemsViewHolder(final @NonNull View view) {
    super(view);

    this.viewModel = new CreatorDashboardRewardItemsHolderViewModel.ViewModel(environment());
    //reward items recycler view
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
  }
}
