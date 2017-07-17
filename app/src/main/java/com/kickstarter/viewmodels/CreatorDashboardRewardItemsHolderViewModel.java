package com.kickstarter.viewmodels;

import android.support.annotation.NonNull;

import com.kickstarter.libs.ActivityViewModel;
import com.kickstarter.libs.Environment;

public interface CreatorDashboardRewardItemsHolderViewModel {

  interface Inputs {}
  interface Outputs {}

  final class ViewModel extends ActivityViewModel<CreatorDashboardRewardItemsViewHolder> implements
    Inputs, Outputs {

    public ViewModel(final @NonNull Environment environment) {
      super(environment);
    }
  }
}
