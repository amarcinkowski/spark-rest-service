export DISPLAY=:99
sh -e /etc/init.d/xvfb start
echo "xvfb ready"
sleep 2
mkdir -p build/logs
java -jar ~/.m2/repository/org/seleniumhq/selenium/selenium-server/2.52.0/selenium-server-2.52.0.jar > selenium.log 2>&1 &
FF=`firefox --version`
echo "selenium running Firefox $FF" 
sleep 2

