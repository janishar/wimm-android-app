package com.whereismymotivation.ui.search

import com.whereismymotivation.data.model.Topic
import com.whereismymotivation.data.repository.SubscriptionRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SuggestionViewModel @Inject constructor(
    loader: Loader,
    messenger: Messenger,
    private val navigator: Navigator,
    private val subscriptionRepository: SubscriptionRepository
) : BaseViewModel(loader, messenger) {

    companion object {
        const val TAG = "SuggestionViewModel"
    }

    init {
        loadTopics()
    }

    private val _topics = MutableStateFlow<List<Topic>>(emptyList())

    val topics = _topics.asStateFlow()

    private fun loadTopics() {
        launchNetwork {
            subscriptionRepository.fetchRecommendedTopics(1, 10)
                .collect {
                    _topics.value = it
                }
        }
    }

    fun selectTopic(topic: Topic) {
        navigator.navigateTo(NavTarget(Destination.Topic.createRoute(topic.id)))
    }

}