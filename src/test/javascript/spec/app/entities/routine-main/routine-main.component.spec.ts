/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RoutineMainComponent from '@/entities/routine-main/routine-main.vue';
import RoutineMainClass from '@/entities/routine-main/routine-main.component';
import RoutineMainService from '@/entities/routine-main/routine-main.service';
import AlertService from '@/shared/alert/alert.service';

const localVue = createLocalVue();
localVue.use(ToastPlugin);

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
localVue.component('font-awesome-icon', {});
localVue.component('b-badge', {});
localVue.directive('b-modal', {});
localVue.component('b-button', {});
localVue.component('router-link', {});

const bModalStub = {
  render: () => {},
  methods: {
    hide: () => {},
    show: () => {},
  },
};

describe('Component Tests', () => {
  describe('RoutineMain Management Component', () => {
    let wrapper: Wrapper<RoutineMainClass>;
    let comp: RoutineMainClass;
    let routineMainServiceStub: SinonStubbedInstance<RoutineMainService>;

    beforeEach(() => {
      routineMainServiceStub = sinon.createStubInstance<RoutineMainService>(RoutineMainService);
      routineMainServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RoutineMainClass>(RoutineMainComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          routineMainService: () => routineMainServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      routineMainServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllRoutineMains();
      await comp.$nextTick();

      // THEN
      expect(routineMainServiceStub.retrieve.called).toBeTruthy();
      expect(comp.routineMains[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      routineMainServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(routineMainServiceStub.retrieve.callCount).toEqual(1);

      comp.removeRoutineMain();
      await comp.$nextTick();

      // THEN
      expect(routineMainServiceStub.delete.called).toBeTruthy();
      expect(routineMainServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
