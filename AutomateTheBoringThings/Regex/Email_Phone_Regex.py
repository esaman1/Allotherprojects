# create regex for phone numbers and emails
# pull text from source
# extract email and phone number with match objects
# return to new text source
import re, pyperclip


phoneRegex = re.compile(r''' ((((\d\d\d)|(\(\d\d\d\)))?
(\s|-)
\d\d\d
(\s|-)
\d\d\d\d))
''', re.VERBOSE)

emailRegex = re.compile(r''' [a-zA-Z0-9_.+]+
@
[a-zA-Z0-9_.+]+
''', re.VERBOSE)

text = pyperclip.paste()
extractedPhone = phoneRegex.findall(text,0)
extractedEmail = emailRegex.findall(text)
phonelist = []
emaillist = []
for phone in extractedPhone:
    phonelist.append(phone[0])
for email in extractedEmail:
    emaillist.append(email)
