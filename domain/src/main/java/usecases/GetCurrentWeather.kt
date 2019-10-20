package usecases

import com.chaeniiz.domain.repositories.CurrentWeatherRepository
import com.chaeniiz.entity.entities.CurrentWeather
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import usecases.base.SingleDisposableUseCase

class GetCurrentWeather(
    private val repository: CurrentWeatherRepository,
    executorScheduler: Scheduler = Schedulers.io(),
    postExecutionScheduler: Scheduler = AndroidSchedulers.mainThread()
) : SingleDisposableUseCase<CurrentWeather>(executorScheduler, postExecutionScheduler) {

    lateinit var city: String

    override fun buildUseCaseSingle(): Single<CurrentWeather> =
        repository.getCurrentWeather(city)
}
