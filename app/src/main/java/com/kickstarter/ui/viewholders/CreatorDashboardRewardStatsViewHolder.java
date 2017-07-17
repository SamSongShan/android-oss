package com.kickstarter.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TextView;

import com.kickstarter.R;
import com.kickstarter.models.Project;
import com.kickstarter.services.apiresponses.ProjectStatsEnvelope;
import com.kickstarter.ui.adapters.CreatorDashboardRewardStatsAdapter;
import com.kickstarter.viewmodels.CreatorDashboardRewardStatsHolderViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.kickstarter.libs.rx.transformers.Transformers.observeForUI;
import static com.kickstarter.libs.utils.ObjectUtils.requireNonNull;

public final class CreatorDashboardRewardStatsViewHolder extends KSViewHolder {

  private final CreatorDashboardRewardStatsHolderViewModel.ViewModel viewModel;
  protected @Bind(R.id.creator_dashboard_reward_stats_table) TableLayout rewardStatsTableLayout;
  protected @Bind(R.id.dashboard_reward_stats_recycler_view) RecyclerView rewardStatsRecyclerView;
  protected @Bind(R.id.reward_minimum_text_view) TextView rewardMinimumTextView;
  protected @Bind(R.id.reward_pledged_text_view) TextView rewardPledgedTextView;
  protected @Bind(R.id.reward_backer_count_text_view) TextView rewardBackerCountTextView;

  public CreatorDashboardRewardStatsViewHolder(final @NonNull View view) {
    super(view);
    this.viewModel = new CreatorDashboardRewardStatsHolderViewModel.ViewModel(environment());
    ButterKnife.bind(this, view);

    final CreatorDashboardRewardStatsAdapter rewardStatsAdapter = new CreatorDashboardRewardStatsAdapter();
    rewardStatsRecyclerView.setAdapter(rewardStatsAdapter);
    final LinearLayoutManager layoutManager = new LinearLayoutManager(context());
    rewardStatsRecyclerView.setLayoutManager(layoutManager);

    this.viewModel.outputs.percentageOfTotalPledged()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(rewardPledgedTextView::setText);

    this.viewModel.outputs.rewardBackerCount()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(rewardBackerCountTextView::setText);

    this.viewModel.outputs.rewardMinimum()
      .compose(bindToLifecycle())
      .compose(observeForUI())
      .subscribe(rewardMinimumTextView::setText);
  }

  @Override
  public void bindData(final @Nullable Object data) throws Exception {
    final Pair<Project, ProjectStatsEnvelope.RewardStats> projectAndRewardStats = requireNonNull((Pair<Project, ProjectStatsEnvelope.RewardStats>) data);
    this.viewModel.inputs.projectAndRewardStats(projectAndRewardStats);
  }

  @Override
  protected void destroy() {
    rewardStatsRecyclerView.setAdapter(null);
  }
}
