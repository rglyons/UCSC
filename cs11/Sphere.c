/* Sphere.c 
 * Robert Lyins
 * rglyons
 * lab7
*/
#include<stdio.h>
#include<stdlib.h>
double main(){
  const double pi = 3.141592654;
  double r, volume, surface;

  printf("Enter the radius of the sphere: ");
  scanf(" %lf", &r);
  volume = (4.0/3.0)*pi*r*r*r;
  surface = 4*pi*r*r;
  printf("The volume is %f and the surface area is %f. \n", volume, surface);
}
