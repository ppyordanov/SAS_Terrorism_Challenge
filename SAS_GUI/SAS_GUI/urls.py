from django.conf.urls import patterns, include, url
from django.contrib import admin
from teroseach import views


urlpatterns = patterns('',
        url(r'^$', views.index, name='index'),
        url(r'^search/$', views.search, name='search'), 
        url(r'^search/results/$', views.result, name='result'),
        url(r'^about/$', views.about, name='about') 
)
