local M = {}

M.privacy_policy = 'https://sites.google.com/view/trickyturns/home'
M.play_store_url = 'http://play.google.com/store/apps/details?id='..sys.get_config('android.package')
M.play_store_developer_url = 'https://play.google.com/store/apps/developer?id=HalfSak+Studios'
M.app_store_url = 'itms-apps://itunes.apple.com/app/id'
M.app_store_developer_url = 'itms-apps://itunes.apple.com/developer/'
M.feedback_email = 'ahamed.bus@gmail.com'

M.STATE_START = 1
M.STATE_PLAYING = 2
M.STATE_GAMEOVER = 3

M.state = M.STATE_START
M.first_game = true
M.interstital_timer = 0
M.system_name = sys.get_sys_info().system_name

M.bg_color = vmath.vector4(0, 218/255, 93/255, 1)
M.color_one = vmath.vector4(1, 1, 1, 1)
M.color_two = vmath.vector4(0, 67/255, 29/255, 1)
M.popup_color = M.bg_color

local savefile_path = sys.get_save_file(string.gsub(sys.get_config('project.title'), '%s+', ''), 'save_file')
local loaded = sys.load(savefile_path)

M.sd = {
	best_score = loaded.best_score or 0,
	sound_on = (loaded.sound_on == nil) and true or loaded.sound_on,
	ads_enabled = (loaded.ads_enabled == nil) and true or loaded.ads_enabled
}

function M.save()
	if sys.save(savefile_path, M.sd) then
		print('save successful')
	else
		print('save failed')
	end
end

function M.distance_between(a, b)
	if (type(a) == 'number' and type(b) == 'number') then
		local distance = math.abs(a - b)
		return distance
	elseif (type(a) == 'userdata' and type(b) == 'userdata') then
		local x_factor = a.x - b.x
		local y_factor = a.y - b.y
		local distance = math.sqrt((x_factor * x_factor) + (y_factor * y_factor))
		return distance
	else
		print('distance_between: a and b not of the same type')
		return nil
	end
end

function M.angle_between(a, b)
	if (a.x == b.x) and (a.y == b.y) then return 0 end
	local x_dist = b.x - a.x
	local y_dist = b.y - a.y
	local angle_between = math.deg(math.atan2(y_dist, x_dist))
	return angle_between
end

function M.duration(speed, a, b)
	local distance = M.distance_between(a, b)
	local duration = 0.1 * distance / speed
	return duration
end

return M
