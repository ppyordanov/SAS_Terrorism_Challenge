from django.shortcuts import render
from django.template import RequestContext
from django.shortcuts import render_to_response

from django.http import HttpResponse

def index(request):
    return HttpResponse("KURAAAACCC")


def search(request):
    context = RequestContext(request)
    return render_to_response('search.html', context)
