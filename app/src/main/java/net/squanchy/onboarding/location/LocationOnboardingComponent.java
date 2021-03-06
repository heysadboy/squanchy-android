package net.squanchy.onboarding.location;

import net.squanchy.injection.ActivityLifecycle;
import net.squanchy.injection.ApplicationComponent;
import net.squanchy.onboarding.Onboarding;
import net.squanchy.onboarding.OnboardingModule;
import net.squanchy.proximity.ProximityFeatureModule;
import net.squanchy.proximity.preconditions.ProximityOptInPersister;
import net.squanchy.proximity.preconditions.ProximityPreconditions;
import net.squanchy.proximity.preconditions.ProximityPreconditionsModule;
import net.squanchy.proximity.preconditions.TaskLauncherActivityModule;
import net.squanchy.service.proximity.injection.ProximityService;

import dagger.Component;

@ActivityLifecycle
@Component(modules = {
        OnboardingModule.class,
        ProximityPreconditionsModule.class,
        ProximityFeatureModule.class,
        TaskLauncherActivityModule.class
}, dependencies = ApplicationComponent.class)
public interface LocationOnboardingComponent {

    ProximityService proximityService();

    Onboarding onboarding();

    ProximityPreconditions proximityPreconditions();

    ProximityOptInPersister proximityOptInPersister();
}
