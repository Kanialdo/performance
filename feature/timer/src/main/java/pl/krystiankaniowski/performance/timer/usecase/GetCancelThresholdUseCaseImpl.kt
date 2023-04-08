package pl.krystiankaniowski.performance.timer.usecase

import pl.krystiankaniowski.performance.domain.timer.GetCancelThresholdUseCase
import pl.krystiankaniowski.performance.model.Seconds
import javax.inject.Inject

class GetCancelThresholdUseCaseImpl @Inject constructor() : GetCancelThresholdUseCase {

    private val threshold: Seconds = Seconds(15)
    override fun invoke(): Seconds = threshold
}