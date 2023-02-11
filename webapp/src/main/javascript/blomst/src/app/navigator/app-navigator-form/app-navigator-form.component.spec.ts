import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppNavigatorFormComponent } from './app-navigator-form.component';

describe('AppNavigatorFormComponent', () => {
  let component: AppNavigatorFormComponent;
  let fixture: ComponentFixture<AppNavigatorFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AppNavigatorFormComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppNavigatorFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
