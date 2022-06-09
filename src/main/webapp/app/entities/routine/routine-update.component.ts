import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IRoutine, Routine } from '@/shared/model/routine.model';
import RoutineService from './routine.service';

const validations: any = {
  routine: {
    register: {
      required,
    },
    type: {
      required,
    },
    desc: {},
  },
};

@Component({
  validations,
})
export default class RoutineUpdate extends Vue {
  @Inject('routineService') private routineService: () => RoutineService;
  @Inject('alertService') private alertService: () => AlertService;

  public routine: IRoutine = new Routine();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.routineId) {
        vm.retrieveRoutine(to.params.routineId);
      }
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.routine.id) {
      this.routineService()
        .update(this.routine)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testJhipsterApplicationApp.routine.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.routineService()
        .create(this.routine)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testJhipsterApplicationApp.routine.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrieveRoutine(routineId): void {
    this.routineService()
      .find(routineId)
      .then(res => {
        this.routine = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {}
}
