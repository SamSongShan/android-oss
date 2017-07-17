package com.kickstarter.viewmodels;

import android.support.annotation.NonNull;
import android.util.Pair;

import com.kickstarter.libs.ActivityViewModel;
import com.kickstarter.libs.Environment;
import com.kickstarter.libs.utils.NumberUtils;
import com.kickstarter.libs.utils.PairUtils;
import com.kickstarter.libs.utils.StringUtils;
import com.kickstarter.models.Project;
import com.kickstarter.services.apiresponses.ProjectStatsEnvelope;
import com.kickstarter.ui.viewholders.CreatorDashboardRewardStatsViewHolder;

import rx.Observable;
import rx.subjects.PublishSubject;

public interface CreatorDashboardRewardStatsHolderViewModel {

  interface Inputs {
    void projectAndRewardStats(Pair<Project, ProjectStatsEnvelope.RewardStats> projectAndRewardStatsEnvelope);
  }

  interface Outputs {
    /* string number of backers */
    Observable<String> rewardBackerCount();

    /* string total pledged for this reward */
    Observable<String> pledgedForReward();

    /* minimum for reward */
    Observable<String> rewardMinimum();

    /* percent of the total that came from this reward */
    Observable<String> percentageOfTotalPledged();
  }

  final class ViewModel extends ActivityViewModel<CreatorDashboardRewardStatsViewHolder> implements
    Inputs, Outputs {

    public ViewModel(final @NonNull Environment environment) {
      super(environment);

      this.rewardStats = projectAndRewardStats
        .map(PairUtils::second);

      this.project = projectAndRewardStats
        .map(PairUtils::first);

      this.rewardBackerCount = this.rewardStats
        .map(ProjectStatsEnvelope.RewardStats::backersCount)
        .map(NumberUtils::format);

      this.pledgedForReward = this.rewardStats
        .map(ProjectStatsEnvelope.RewardStats::pledged)
        .map(NumberUtils::format);

      this.rewardMinimum = this.rewardStats
        .map(ProjectStatsEnvelope.RewardStats::minimum)
        .map(NumberUtils::format);

      this.percentageOfTotalPledged = this.projectAndRewardStats
        .map(projectRewardStats -> {
          final Project p = projectRewardStats.first;
          final ProjectStatsEnvelope.RewardStats rs = projectRewardStats.second;
          return NumberUtils.flooredPercentage((rs.pledged() / p.pledged()) * 100);
        })
        .map(StringUtils::wrapInParentheses)
        .zipWith(pledgedForReward, (percentOfTotal, pledgedForReward) -> pledgedForReward + " " + percentOfTotal + "%");
    }

    public final Inputs inputs = this;
    public final Outputs outputs = this;

    private final PublishSubject<Pair<Project, ProjectStatsEnvelope.RewardStats>> projectAndRewardStats = PublishSubject.create();
    private final Observable<String> rewardBackerCount;
    private final Observable<ProjectStatsEnvelope.RewardStats> rewardStats;
    private final Observable<String> pledgedForReward;
    private final Observable<Project> project;
    private final Observable<String> percentageOfTotalPledged;
    private final Observable<String> rewardMinimum;

    @Override
    public void projectAndRewardStats(final @NonNull Pair<Project, ProjectStatsEnvelope.RewardStats> projectAndRewardStats) {
      this.projectAndRewardStats.onNext(projectAndRewardStats);
    }

    @Override public @NonNull Observable<String> rewardBackerCount() {
      return this.rewardBackerCount;
    }
    @Override public @NonNull Observable<String> pledgedForReward() {
      return this.pledgedForReward;
    }
    @Override public @NonNull Observable<String> percentageOfTotalPledged() {
      return this.percentageOfTotalPledged;
    }
    @Override public @NonNull Observable<String> rewardMinimum() {
      return this.rewardMinimum;
    }

  }
}
