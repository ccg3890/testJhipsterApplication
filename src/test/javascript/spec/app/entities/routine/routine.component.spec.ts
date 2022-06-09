/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import { ToastPlugin } from 'bootstrap-vue';

import * as config from '@/shared/config/config';
import RoutineComponent from '@/entities/routine/routine.vue';
import RoutineClass from '@/entities/routine/routine.component';
import RoutineService from '@/entities/routine/routine.service';
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
  describe('Routine Management Component', () => {
    let wrapper: Wrapper<RoutineClass>;
    let comp: RoutineClass;
    let routineServiceStub: SinonStubbedInstance<RoutineService>;

    beforeEach(() => {
      routineServiceStub = sinon.createStubInstance<RoutineService>(RoutineService);
      routineServiceStub.retrieve.resolves({ headers: {} });

      wrapper = shallowMount<RoutineClass>(RoutineComponent, {
        store,
        i18n,
        localVue,
        stubs: { bModal: bModalStub as any },
        provide: {
          routineService: () => routineServiceStub,
          alertService: () => new AlertService(),
        },
      });
      comp = wrapper.vm;
    });

    it('Should call load all on init', async () => {
      // GIVEN
      routineServiceStub.retrieve.resolves({ headers: {}, data: [{ id: 'ABC' }] });

      // WHEN
      comp.retrieveAllRoutines();
      await comp.$nextTick();

      // THEN
      expect(routineServiceStub.retrieve.called).toBeTruthy();
      expect(comp.routines[0]).toEqual(expect.objectContaining({ id: 'ABC' }));
    });
    it('Should call delete service on confirmDelete', async () => {
      // GIVEN
      routineServiceStub.delete.resolves({});

      // WHEN
      comp.prepareRemove({ id: 'ABC' });
      expect(routineServiceStub.retrieve.callCount).toEqual(1);

      comp.removeRoutine();
      await comp.$nextTick();

      // THEN
      expect(routineServiceStub.delete.called).toBeTruthy();
      expect(routineServiceStub.retrieve.callCount).toEqual(2);
    });
  });
});
