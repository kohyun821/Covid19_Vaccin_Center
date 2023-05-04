import android.content.Context
import com.example.covid19_vaccin_center.Splash.data.repository.VaccineRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideVaccineRepository(context: Context): VaccineRepository {
        return VaccineRepository(context)
    }
}
