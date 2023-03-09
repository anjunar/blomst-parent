import {Directive, Input, TemplateRef} from '@angular/core';

@Directive({selector: 'ng-template[typedTemplate]'})
export class TypedTemplateDirective<TypeToken> {

  @Input() typedTemplate!: TypeToken;

  constructor(private contentTemplate: TemplateRef<TypeToken>) {}

  static ngTemplateContextGuard<TypeToken>(dir: TypedTemplateDirective<TypeToken>, ctx: unknown): ctx is TypeToken{ return true; }
}
