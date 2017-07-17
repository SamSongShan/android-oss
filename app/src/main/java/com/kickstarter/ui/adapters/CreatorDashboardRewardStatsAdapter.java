package com.kickstarter.ui.adapters;


import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Pair;
import android.view.View;

import com.kickstarter.R;
import com.kickstarter.models.Project;
import com.kickstarter.services.apiresponses.ProjectStatsEnvelope;
import com.kickstarter.ui.viewholders.CreatorDashboardRewardStatsViewHolder;
import com.kickstarter.ui.viewholders.KSViewHolder;

import java.util.Collections;

public class CreatorDashboardRewardStatsAdapter extends KSAdapter {

  protected @LayoutRes int layout(final @NonNull SectionRow sectionRow) {
    return R.layout.dashboard_reward_stats_table_view;
  }

  protected @NonNull KSViewHolder viewHolder(final @LayoutRes int layout, final @NonNull View view) {
    return new CreatorDashboardRewardStatsViewHolder(view);
  }

  public void takeProjectAndStats(final @NonNull Pair<Project, ProjectStatsEnvelope> projectAndStatsEnvelope) {
    sections().clear();
    sections().add(Collections.singletonList(projectAndStatsEnvelope));
    notifyDataSetChanged();
  }
}
