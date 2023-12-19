package com.whereismymotivation.ui.mentors

import com.whereismymotivation.data.model.Mentor
import com.whereismymotivation.data.repository.MentorRepository
import com.whereismymotivation.ui.base.BaseViewModel
import com.whereismymotivation.ui.common.progress.Loader
import com.whereismymotivation.ui.common.snackbar.Messenger
import com.whereismymotivation.ui.navigation.Destination
import com.whereismymotivation.ui.navigation.NavTarget
import com.whereismymotivation.ui.navigation.Navigator
import com.whereismymotivation.utils.network.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MentorsViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    loader: Loader,
    messenger: Messenger,
    private val navigator: Navigator,
    private val mentorRepository: MentorRepository,
) : BaseViewModel(networkHelper, loader, messenger) {

    companion object {
        const val TAG = "MentorsViewModel"
    }

    private val _mentors = MutableStateFlow<List<Mentor>>(emptyList())

    val mentors = _mentors.asStateFlow()

    init {
        loadMentors()
    }

    fun selectMentor(mentor: Mentor) {
        navigator.navigateTo(NavTarget(Destination.Mentor.createRoute(mentor.id)))
    }

    private fun loadMentors() {
        launchNetwork {
            mentorRepository.fetchSubscriptionMentorList()
                .collect {
                    _mentors.value = it
                }
        }
    }

}