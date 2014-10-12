from django.conf.urls import patterns, include, url
from django.contrib import admin
from teroseach import views


urlpatterns = patterns('',
        url(r'^$', views.index, name='index'),
<<<<<<< HEAD
        url(r'^search/$', views.search, name='search'), 
        url(r'^search/results/$', views.result, name='result'),
        url(r'^about/$', views.about, name='about') 
=======
        url(r'^search/$', views.search, name='search'),
        url(r'^about/$', views.about, name='about')     
>>>>>>> 255cf01320a13cb1b024d052e184f63d38534b96
)
