echo .project>ignore.list
echo .classpath>>ignore.list
echo .settings>>ignore.list
echo target>>ignore.list
FOR /F "tokens=*" %%G IN ('DIR *target /AD /B /S') do (
svn propset svn:ignore --file ignore.list %%G\..
)
del ignore.list