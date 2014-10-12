from django.shortcuts import render
from django.template import RequestContext
from django.shortcuts import render_to_response
import json
from django.http import HttpResponse
import urllib2

from teroseach.models import Wikievent

def index(request):
    return HttpResponse("OK")

def search(request):
    context = RequestContext(request)
    return render_to_response('search.html', context)

def result(request):
    context = RequestContext(request)
    message = ''
    if 'search' in request.GET:

        message = request.GET['search']

    wikiTitle = key_words_extractor(message)
    results = []
    context_dic = {"msg": wikiTitle,
                   "results" : results                   
                   }
    
    event = Wikievent.objects.filter(title = wikiTitle).all()
    if (len(event) == 0):
        return render(request,'result.html', context_dic)
    
    parentIds = event[0].parents.split(',')
        
    
    for id in parentIds:
        dic = {}
        event = Wikievent.objects.filter(eventid = id).all()[0]
        dic['id'] =  event.eventid
        dic['title'] = event.title
        dic['url'] = event.url
        results.append(dic)
    
    
    return render(request,'result.html', context_dic)

def about(request):
    context = RequestContext(request)
    return render_to_response('about.html', context)


def key_words_extractor(query):
    words = query.split(' ')
    clear_words = []
    for word in words:
        if word not in STOPWORDS:
            clear_words.append(word)
            
    url = "http://en.wikipedia.org/w/api.php?format=json&action=query&list=search&srsearch="
    
    count = 0
    for word in clear_words:
        if (count == 0):
            url += word
        else:
            url += "%20" + word
        count += 1
    
    response = urllib2.urlopen(url)
    jsonStr = response.read()
    decoded = json.loads(jsonStr)

    title = decoded['query']['search'][0]['title']
    return title


STOPWORDS = ["", "a", "about", "above",
"across", "after", "afterwards", "again", "against", "all",
"almost", "alone", "along", "already", "also", "although",
"always", "am", "among", "amongst", "amoungst", "amount", "an",
"and", "another", "any", "anyhow", "anyone", "anything", "anyway",
"anywhere", "are", "around", "as", "at", "back", "be", "became",
"because", "become", "becomes", "becoming", "been", "before",
"beforehand", "behind", "being", "below", "beside", "besides",
"between", "beyond", "bill", "both", "bottom", "but", "by", "call",
"can", "cannot", "cant", "co", "con", "could", "couldnt", "cry",
"de", "describe", "detail", "do", "done", "down", "due", "during",
"each", "eg", "eight", "either", "eleven", "else", "elsewhere",
"empty", "enough", "etc", "even", "ever", "every", "everyone",
"everything", "everywhere", "except", "few", "fifteen", "fify",
"fill", "find", "fire", "first", "five", "for", "former",
"formerly", "forty", "found", "four", "from", "front", "full",
"further", "get", "give", "go", "had", "has", "hasnt", "have",
"he", "hence", "her", "here", "hereafter", "hereby", "herein",
"hereupon", "hers", "herself", "him", "himself", "his", "how",
"however", "hundred", "ie", "if", "in", "inc", "indeed",
"interest", "into", "is", "it", "its", "itself", "keep", "last",
"latter", "latterly", "least", "less", "ltd", "made", "many",
"may", "me", "meanwhile", "might", "mill", "mine", "more",
"moreover", "most", "mostly", "move", "much", "must", "my",
"myself", "name", "namely", "neither", "never", "nevertheless",
"next", "nine", "no", "nobody", "none", "noone", "nor", "not",
"nothing", "now", "nowhere", "of", "off", "often", "on", "once",
"one", "only", "onto", "or", "other", "others", "otherwise", "our",
"ours", "ourselves", "out", "over", "own", "part", "per",
"perhaps", "please", "put", "rather", "re", "same", "see", "seem",
"seemed", "seeming", "seems", "serious", "several", "she",
"should", "show", "side", "since", "sincere", "six", "sixty", "so",
"some", "somehow", "someone", "something", "sometime", "sometimes",
"somewhere", "still", "such", "system", "take", "ten", "than",
"that", "the", "their", "them", "themselves", "then", "thence",
"there", "thereafter", "thereby", "therefore", "therein",
"thereupon", "these", "they", "thickv", "thin", "third", "this",
"those", "though", "three", "through", "throughout", "thru",
"thus", "to", "together", "too", "top", "toward", "towards",
"twelve", "twenty", "two", "un", "under", "unknown", "until", "up", "upon",
"us", "very", "via", "was", "we", "well", "were", "what",
"whatever", "when", "whence", "whenever", "where", "whereafter",
"whereas", "whereby", "wherein", "whereupon", "wherever",
"whether", "which", "while", "whither", "who", "whoever", "whole",
"whom", "whose", "why", "will", "with", "within", "without",
"would", "yet", "you", "your", "yours", "yourself", "yourselves",
"the"];
