import { Component, Vue, Inject } from 'vue-property-decorator';

import { required } from 'vuelidate/lib/validators';

import AlertService from '@/shared/alert/alert.service';

import { IRoutineMain, RoutineMain } from '@/shared/model/routine-main.model';
import RoutineMainService from './routine-main.service';

const validations: any = {
  routineMain: {
    registerid: {
      required,
    },
    description: {},
  },
};

@Component({
  validations,
})
export default class RoutineMainUpdate extends Vue {
  @Inject('routineMainService') private routineMainService: () => RoutineMainService;
  @Inject('alertService') private alertService: () => AlertService;

  public routineMain: IRoutineMain = new RoutineMain();
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.routineMainId) {
        vm.retrieveRoutineMain(to.params.routineMainId);
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
    if (this.routineMain.id) {
      this.routineMainService()
        .update(this.routineMain)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testJhipsterApplicationApp.routineMain.updated', { param: param.id });
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
      this.routineMainService()
        .create(this.routineMain)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('testJhipsterApplicationApp.routineMain.created', { param: param.id });
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

  public retrieveRoutineMain(routineMainId): void {
    this.routineMainService()
      .find(routineMainId)
      .then(res => {
        this.routineMain = res;
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
