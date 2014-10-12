from django.shortcuts import render
from django.template import RequestContext
from django.shortcuts import render_to_response

from django.http import HttpResponse

def index(request):
    return HttpResponse("OK")

def search(request):
    context = RequestContext(request)
    return render_to_response('search.html', context)

def result(request):
    context = RequestContext(request)
    
    if 'search' in request.GET:
        message = 'You searched for: ' + request.GET['search']
    else:
        message = 'You submitted an empty form.'
        
    context_dic = {"msg": message}
    return render(request,'result.html', context_dic)
	
def about(request):
    context = RequestContext(request)
    return render_to_response('about.html', context)

